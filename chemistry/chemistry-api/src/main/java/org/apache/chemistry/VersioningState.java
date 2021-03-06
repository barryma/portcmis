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
 * State of a document when it is created.
 */
public enum VersioningState {

    /**
     * Create the document as a private working copy.
     */
    CHECKED_OUT("checkedout"),

    /**
     * Create the document as a checked in minor version.
     */
    MINOR("minor"),

    /**
     * Create the document as a checked in major version.
     */
    MAJOR("major");

    private final String value;

    private VersioningState(String value) {
        this.value = value;
    }

    private static final Map<String, VersioningState> all = new HashMap<String, VersioningState>();
    static {
        for (VersioningState o : values()) {
            all.put(o.value, o);
        }
    }

    public static VersioningState get(String value) {
        VersioningState o = all.get(value);
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
