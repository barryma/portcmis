/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors:
 *     Florent Guillaume, Nuxeo
 */
package org.apache.chemistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Flag specifying what version of a given document is to be returned.
 */
public enum ReturnVersion {

    /**
     * Returns the document specified.
     */
    THIS("this"),

    /**
     * Returns the latest version of the document.
     */
    LATEST("latest"),

    /**
     * Returns the latest major version of the document.
     */
    LATEST_MAJOR("latestmajor");

    private final String value;

    private ReturnVersion(String value) {
        this.value = value;
    }

    private static final Map<String, ReturnVersion> all = new HashMap<String, ReturnVersion>();
    static {
        for (ReturnVersion o : values()) {
            all.put(o.value, o);
        }
    }

    public static ReturnVersion get(String value) {
        ReturnVersion o = all.get(value);
        if (o == null) {
            throw new IllegalArgumentException(value);
        }
        return o;
    }

    @Override
    public String toString() {
        return value;
    }
}
