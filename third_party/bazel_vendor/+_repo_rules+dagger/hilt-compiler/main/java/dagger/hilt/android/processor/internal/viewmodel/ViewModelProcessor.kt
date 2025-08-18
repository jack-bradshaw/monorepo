/*
 * Copyright (C) 2020 The Dagger Authors.
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

package dagger.hilt.android.processor.internal.viewmodel

import androidx.room.compiler.processing.ExperimentalProcessingApi
import com.google.auto.service.AutoService
import dagger.hilt.processor.internal.BaseProcessingStep
import dagger.hilt.processor.internal.JavacBaseProcessingStepProcessor
import javax.annotation.processing.Processor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType

/** Annotation processor for @ViewModelInject. */
@AutoService(Processor::class)
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.ISOLATING)
class ViewModelProcessor : JavacBaseProcessingStepProcessor() {
  @OptIn(ExperimentalProcessingApi::class)
  override fun processingStep(): BaseProcessingStep = ViewModelProcessingStep(xProcessingEnv)
}
