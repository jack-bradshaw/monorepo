/*
 * Copyright (C) 2023 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app

import dagger.Component
import library1.AssistedFoo
import library1.Foo
import library1.MyBaseComponent
import library1.MyComponentDependency
import library1.MyComponentDependencyBinding
import library1.MyComponentModule
import library1.MySubcomponentWithBuilder
import library1.MySubcomponentWithFactory
import library1.MyQualifier
import javax.inject.Singleton
import library1.MyComponentModule.ScopedQualifiedBindsType
import library1.MyComponentModule.ScopedUnqualifiedBindsType
import library1.MyComponentModule.UnscopedQualifiedBindsType
import library1.MyComponentModule.UnscopedUnqualifiedBindsType
import library1.MyComponentModule.ScopedQualifiedProvidesType
import library1.MyComponentModule.ScopedUnqualifiedProvidesType
import library1.MyComponentModule.UnscopedQualifiedProvidesType
import library1.MyComponentModule.UnscopedUnqualifiedProvidesType

@Singleton
@Component(dependencies = [MyComponentDependency::class], modules = [MyComponentModule::class])
internal abstract class MyComponent : MyBaseComponent() {
    abstract fun foo(): Foo
    @MyQualifier
    abstract fun scopedQualifiedBindsType(): ScopedQualifiedBindsType
    abstract fun assistedFooFactory(): AssistedFoo.Factory
    @MyQualifier
    abstract fun unscopedQualifiedBindsType(): UnscopedQualifiedBindsType
    abstract fun scopedUnqualifiedBindsType(): ScopedUnqualifiedBindsType
    @MyQualifier
    abstract fun scopedQualifiedProvidesType(): ScopedQualifiedProvidesType
    abstract fun unscopedUnqualifiedBindsType(): UnscopedUnqualifiedBindsType
    @MyQualifier
    abstract fun unscopedQualifiedProvidesType(): UnscopedQualifiedProvidesType
    abstract fun scopedUnqualifiedProvidesType(): ScopedUnqualifiedProvidesType
    abstract fun unscopedUnqualifiedProvidesType(): UnscopedUnqualifiedProvidesType
    abstract fun mySubcomponentWithFactory(): MySubcomponentWithFactory.Factory
    abstract fun mySubcomponentWithBuilder(): MySubcomponentWithBuilder.Builder
    @MyQualifier
    abstract fun qualifiedMyComponentDependencyBinding(): MyComponentDependencyBinding
    abstract fun unqualifiedMyComponentDependencyBinding(): MyComponentDependencyBinding


    @Component.Factory
    internal abstract class Factory : MyBaseComponent.Factory() {
        abstract override fun create(
            myComponentModule: MyComponentModule,
            myComponentDependency: MyComponentDependency
        ): MyComponent
    }
}
