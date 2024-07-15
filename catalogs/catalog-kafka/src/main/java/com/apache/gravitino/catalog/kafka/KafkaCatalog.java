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

package com.apache.gravitino.catalog.kafka;

import com.apache.gravitino.connector.BaseCatalog;
import com.apache.gravitino.connector.CatalogOperations;
import com.apache.gravitino.connector.PropertiesMetadata;
import com.apache.gravitino.connector.capability.Capability;
import java.util.Map;

/**
 * Kafka catalog is a messaging catalog that can manage topics on the Apache Kafka messaging system.
 */
public class KafkaCatalog extends BaseCatalog<KafkaCatalog> {

  static final KafkaCatalogPropertiesMetadata CATALOG_PROPERTIES_METADATA =
      new KafkaCatalogPropertiesMetadata();
  static final KafkaSchemaPropertiesMetadata SCHEMA_PROPERTIES_METADATA =
      new KafkaSchemaPropertiesMetadata();
  static final KafkaTopicPropertiesMetadata TOPIC_PROPERTIES_METADATA =
      new KafkaTopicPropertiesMetadata();

  @Override
  public String shortName() {
    return "kafka";
  }

  @Override
  protected CatalogOperations newOps(Map<String, String> config) {
    KafkaCatalogOperations ops = new KafkaCatalogOperations();
    return ops;
  }

  @Override
  protected Capability newCapability() {
    return new KafkaCatalogCapability();
  }

  @Override
  public PropertiesMetadata catalogPropertiesMetadata() throws UnsupportedOperationException {
    return CATALOG_PROPERTIES_METADATA;
  }

  @Override
  public PropertiesMetadata schemaPropertiesMetadata() throws UnsupportedOperationException {
    return SCHEMA_PROPERTIES_METADATA;
  }

  @Override
  public PropertiesMetadata topicPropertiesMetadata() throws UnsupportedOperationException {
    return TOPIC_PROPERTIES_METADATA;
  }
}
