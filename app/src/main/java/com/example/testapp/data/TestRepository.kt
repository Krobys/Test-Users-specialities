package com.example.testapp.data

import arrow.core.Either
import com.example.testapp.data.database.TestDatabase
import com.example.testapp.data.network.DataWrapper
import com.example.testapp.data.network.TestApi
import com.example.testapp.data.network.response.Error
import com.example.testapp.data.network.response.UsersResponse
import javax.inject.Inject

class TestRepository @Inject constructor(
    private val testNetwork: TestApi,
    private val TestDatabase: TestDatabase
) {

    suspend fun requestUsersList(onLoadCallback: suspend ((Either<Error, DataWrapper<UsersResponse>>) -> Unit)) {
        repositoryTreatment(onLoadCallback = onLoadCallback,
            loadLocal = {
                return@repositoryTreatment UsersResponse(it.usersDao().getUsers())
            }, loadRemote = {
                return@repositoryTreatment it.getUsersList()
            }, onCacheFromRemote = { data, database ->
                database.usersDao().run {
                    clearTable()
                    setUsers(users = data.response)
                }
            })
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private suspend fun <T> repositoryTreatment(
        loadRemote: suspend ((testNetwork: TestApi) -> Either<Error, T>),
        loadLocal: suspend ((TestDatabase: TestDatabase) -> T?),
        onCacheFromRemote: suspend ((T, TestDatabase: TestDatabase) -> Unit),
        onLoadCallback: suspend (Either<Error, DataWrapper<T>>) -> Unit
    ) {
        //загрузить из базы и засетить
        val localData = loadLocal.invoke(TestDatabase)
        localData?.let {
            onLoadCallback.invoke(wrapLocalData(localData, false))
        }


        val remoteData = loadRemote.invoke(testNetwork)

        remoteData.fold(ifLeft = {
            //прокидываем ошибку дальше, не загружаем в кеш и не возвращаем данные из базы
            val remoteDataWrapper = wrapRemoteData(remoteData)
            onLoadCallback.invoke(remoteDataWrapper)
        }, ifRight = {
            //закешить из интернета
            onCacheFromRemote.invoke(it, TestDatabase)

            //загрузить из базы кеш и засетить(нужно что бы айди у айтемов были те, что в базе)
            loadLocal.invoke(TestDatabase)?.let {
                onLoadCallback.invoke(wrapLocalData(it, true))
            }

        })


    }

    private fun <T> wrapLocalData(data: T, isFromRemote: Boolean): Either<Error, DataWrapper<T>> {
        return Either.Right(DataWrapper(isFromRemote, data))
    }

    private fun <T> wrapRemoteData(data: Either<Error, T>): Either<Error, DataWrapper<T>> {
        return data.fold(ifLeft = {
            Either.Left(it)
        }, ifRight = {
            Either.Right(DataWrapper(isFromRemote = true, data = it))
        })
    }
}