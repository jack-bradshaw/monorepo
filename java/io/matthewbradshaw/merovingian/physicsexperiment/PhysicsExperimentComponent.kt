package io.matthewbradshaw.merovingian.physicsexperiment

import dagger.Component
import io.matthewbradshaw.merovingian.MerovingianComponent
import io.matthewbradshaw.merovingian.physicsexperiment.items.Lab
import io.matthewbradshaw.merovingian.physicsexperiment.items.ItemsModule
import io.matthewbradshaw.merovingian.physicsexperiment.materials.MaterialsModule
import io.matthewbradshaw.merovingian.physicsexperiment.support.SupportModule

@PhysicsExperimentScope
@Component(
  modules = [MaterialsModule::class, SupportModule::class, ItemsModule::class],
  dependencies = [MerovingianComponent::class]
)
interface PhysicsExperimentComponent {

  fun world(): Lab

  @Component.Builder
  interface Builder {
    fun setMerovingianComponent(merovingianComponent: MerovingianComponent): Builder
    fun build(): PhysicsExperimentComponent
  }
}

fun physicsExperiment(merovingian: MerovingianComponent) =
  DaggerPhysicsExperimentComponent.builder().setMerovingianComponent(merovingian).build()