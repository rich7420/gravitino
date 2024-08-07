/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.datastrato.gravitino.messaging;

import com.datastrato.gravitino.Auditable;
import com.datastrato.gravitino.annotation.Evolving;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * An interface representing a topic under a schema {@link com.datastrato.gravitino.Namespace}. A
 * topic is a message queue that is managed by Apache Gravitino. Users can create/drop/alter a topic
 * on the Message Queue system like Apache Kafka, Apache Pulsar, etc.
 *
 * <p>{@link Topic} defines the basic properties of a topic object. A catalog implementation with
 * {@link TopicCatalog} should implement this interface.
 */
@Evolving
public interface Topic extends Auditable {

  /** @return Name of the topic */
  String name();

  /** @return The comment of the topic object. Null is returned if no comment is set. */
  @Nullable
  default String comment() {
    return null;
  }

  /** @return The properties of the topic object. Empty map is returned if no properties are set. */
  default Map<String, String> properties() {
    return Collections.emptyMap();
  }
}
