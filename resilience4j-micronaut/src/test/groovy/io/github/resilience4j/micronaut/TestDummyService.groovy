/*
 * Copyright 2020 Michael Pollind
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.resilience4j.micronaut

import io.micronaut.context.annotation.Executable
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

abstract class TestDummyService {

    abstract String sync();

    abstract String syncWithParam(String param);

    abstract CompletableFuture<String> completable();

    abstract CompletableFuture<String> completableWithParam(String param);

    abstract Flowable<String> flowable();

    String syncError() {
        throw new RuntimeException("Test");
    }

    Flowable<String> flowableError() {
        return Flowable.error(new RuntimeException("Test"));
    }

    CompletableFuture<String> completableFutureError() {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Test"));
        return future
    }

    @Executable
    Flowable<String> flowableRecovery() {
        return Flowable.just('recovered');
    }

    @Executable
    CompletableFuture<String> completableRecovery() {
        return CompletableFuture.supplyAsync({ -> 'recovered' });
    }

    @Executable
    CompletableFuture<String> completableRecoveryParam(String parameter) {
        return CompletableFuture.supplyAsync({ -> parameter });
    }

    @Executable
    String syncRecovery() {
        return "recovered"
    }

    @Executable
    String syncRecoveryParam(String parameter) {
        return parameter
    }
}
