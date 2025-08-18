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

package library1

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject
import library2.MyTransitiveAnnotation
import library2.MyTransitiveType

/**
 * A class used to test that Dagger won't fail when non-dagger related annotations cannot be
 * resolved.
 *
 *
 * During the compilation of `:app`, [MyTransitiveAnnotation] will no longer be on
 * the classpath. In most cases, Dagger shouldn't care that the annotation isn't on the classpath
 */
@MyTransitiveAnnotation
@MyAnnotation(MyTransitiveType.VALUE)
@MyOtherAnnotation(MyTransitiveType::class)
class AssistedFoo : FooBase {
    @MyTransitiveAnnotation
    @MyAnnotation(MyTransitiveType.VALUE)
    @MyOtherAnnotation(MyTransitiveType::class)
    lateinit var nonDaggerField: MyTransitiveType

    @MyTransitiveAnnotation
    @MyAnnotation(MyTransitiveType.VALUE)
    @MyOtherAnnotation(MyTransitiveType::class)
    @Inject
    @MyQualifier
    lateinit var daggerField: Dep

    @MyTransitiveAnnotation
    @MyAnnotation(MyTransitiveType.VALUE)
    @MyOtherAnnotation(MyTransitiveType::class)
    internal constructor(
        @MyTransitiveAnnotation
        @MyAnnotation(MyTransitiveType.VALUE)
        @MyOtherAnnotation(MyTransitiveType::class)
        nonDaggerParameter: MyTransitiveType
    ) : super(nonDaggerParameter)

    @MyTransitiveAnnotation
    @MyAnnotation(MyTransitiveType.VALUE)
    @MyOtherAnnotation(MyTransitiveType::class)
    @AssistedInject
    internal constructor(
        @MyTransitiveAnnotation
        @MyAnnotation(MyTransitiveType.VALUE)
        @MyOtherAnnotation(MyTransitiveType::class)
        @Assisted
        i: Int,
        @MyTransitiveAnnotation
        @MyAnnotation(MyTransitiveType.VALUE)
        @MyOtherAnnotation(MyTransitiveType::class)
        @MyQualifier
        dep: Dep
    ) : super(dep)

    @MyTransitiveAnnotation
    @MyAnnotation(MyTransitiveType.VALUE)
    @MyOtherAnnotation(MyTransitiveType::class)
    fun nonDaggerMethod(
        @MyTransitiveAnnotation
        @MyAnnotation(MyTransitiveType.VALUE)
        @MyOtherAnnotation(MyTransitiveType::class)
        nonDaggerParameter: MyTransitiveType
    ): MyTransitiveType = nonDaggerParameter

    @MyTransitiveAnnotation
    @MyAnnotation(MyTransitiveType.VALUE)
    @MyOtherAnnotation(MyTransitiveType::class)
    @Inject
    fun daggerMethod(
        @MyTransitiveAnnotation
        @MyAnnotation(MyTransitiveType.VALUE)
        @MyOtherAnnotation(MyTransitiveType::class)
        @MyQualifier
        dep: Dep
    ) {}

    @MyTransitiveAnnotation
    @MyAnnotation(MyTransitiveType.VALUE)
    @MyOtherAnnotation(MyTransitiveType::class)
    @AssistedFactory
    interface Factory {
        @MyTransitiveAnnotation
        @MyAnnotation(MyTransitiveType.VALUE)
        @MyOtherAnnotation(MyTransitiveType::class)
        fun create(
            @MyTransitiveAnnotation
            @MyAnnotation(MyTransitiveType.VALUE)
            @MyOtherAnnotation(MyTransitiveType::class)
            i: Int
        ): AssistedFoo
    }
}
