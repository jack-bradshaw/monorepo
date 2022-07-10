package io.matthewbradshaw.frankl.physicsexperiment

import dagger.Component
import io.matthewbradshaw.frankl.MerovingianComponent
import io.matthewbradshaw.frankl.physicsexperiment.items.Lab
import io.matthewbradshaw.frankl.physicsexperiment.items.ItemsModule
import io.matthewbradshaw.frankl.physicsexperiment.materials.MaterialsModule
import io.matthewbradshaw.frankl.physicsexperiment.support.SupportModule

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