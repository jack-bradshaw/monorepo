package io.matthewbradshaw.jockstrap.physics.experiment

import dagger.Component
import java.io.matthewbradshaw.jockstrap.MerovingianComponent
import io.matthewbradshaw.jockstrap.physics.experiment.items.Lab
import io.matthewbradshaw.jockstrap.physics.experiment.items.ItemsModule
import io.matthewbradshaw.jockstrap.physics.experiment.materials.MaterialsModule
import io.matthewbradshaw.jockstrap.physics.experiment.support.SupportModule

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