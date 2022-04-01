package com.wdretzer.doctordigital.repository

import com.wdretzer.doctordigital.data.Api
import com.wdretzer.doctordigital.data.DataBaseFactory
import com.wdretzer.doctordigital.data.extension.updateStatus
import com.wdretzer.doctordigital.model.DoctorsList
import com.wdretzer.doctordigital.model.DoctorsResponse
import com.wdretzer.doctordigital.model.toDoctorEntity
import com.wdretzer.doctordigital.network.DataResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception


class ApiDoctorRepository(
    private val api: Api = Api.api,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val dao = DataBaseFactory.getDataBAse().doctorDao()

    fun getDoctors(pageNumber: Int = 1) = flow<DataResult<DoctorsResponse>> {
        val localItens = dao.listAll()
        val response = api.doctors(pageNumber)
        val novaLista = response.doctors.map { apiItem ->
            if (localItens.filter { it.apiId == apiItem.id }.getOrNull(0) != null) apiItem.copy(
                isFavourite = true
            ) else apiItem
        }
        emit(DataResult.Success(response.copy(doctors = novaLista)))
    }.updateStatus().flowOn(dispatcher)


    fun getFavourite() = flow<MutableList<DoctorsList>> {
        val localItens = dao.listAll().map { DoctorsList(it) }
        emit((localItens as MutableList<DoctorsList>))
    }.flowOn(dispatcher)


    fun addOrRemoveFavourite(item: DoctorsList) = flow {
        try {
            val numeroRegistro = dao.countApiId(item.id)
            val itemExist = numeroRegistro >= 1

            if (itemExist) {
                dao.deleteByApiId(item.id)
            } else {
                dao.insert(item.toDoctorEntity())
            }

            emit(DataResult.Success(item.copy(isFavourite = itemExist.not())))
        } catch (e: Exception) {
            emit(DataResult.Error(IllegalStateException()))
        }
    }.updateStatus().flowOn(dispatcher)

    companion object {
        val instance by lazy { ApiDoctorRepository() }
    }
}
