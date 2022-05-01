package app.beelabs.com.coconut_mvvm.sample.domain.usecases

import app.beelabs.coconut.mvvm.base.Resource
import app.beelabs.coconut.mvvm.base.helper.BaseUseCase
import app.beelabs.com.coconut_mvvm.sample.model.api.response.LocationResponse
import app.beelabs.com.coconut_mvvm.sample.model.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationUseCases @Inject constructor(
    private val locationRepository: LocationRepository
) : BaseUseCase<Resource<LocationResponse>>() {

    override suspend fun performAction(): Flow<Resource<LocationResponse>> {
        return locationRepository.getLocationCaroutine()
    }
}