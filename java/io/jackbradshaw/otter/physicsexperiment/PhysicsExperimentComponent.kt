package io.jackbradshaw.otter.physics.experiment

import dagger.Component
import io.jackbradshaw.otter.physics.experiment.items.ItemsModule
import io.jackbradshaw.otter.physics.experiment.items.Lab
import io.jackbradshaw.otter.physics.experiment.materials.MaterialsModule
import io.jackbradshaw.otter.physics.experiment.support.SupportModule
import java.io.jackbradshaw.otter.MerovingianComponent

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