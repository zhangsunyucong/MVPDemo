/*
 * Copyright (C) 2016 JokAr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.librarys.rxupload.model.network.result;

import com.example.librarys.rxupload.model.network.exception.CustomizeException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by JokAr on 2017/3/6.
 */

public class HttpResultFunc<T> implements Function<UploadResult<T>, T> {


    /**
     * Apply some calculation to the input value and return some other value.
     *
     * @param result the input value
     * @return the output value
     * @throws Exception on error
     */
    @Override
    public T apply(@NonNull UploadResult<T> result) throws Exception {
        if (result.getCode() == 200) {
            return result.getData();
        } else {
            throw new CustomizeException(result.getMsg());
        }
    }
}
