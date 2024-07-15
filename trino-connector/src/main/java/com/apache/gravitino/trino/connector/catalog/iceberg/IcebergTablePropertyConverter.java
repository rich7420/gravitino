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

package com.apache.gravitino.trino.connector.catalog.iceberg;

import static com.apache.gravitino.catalog.lakehouse.iceberg.IcebergTablePropertiesMetadata.LOCATION;
import static com.apache.gravitino.trino.connector.catalog.iceberg.IcebergPropertyMeta.ICEBERG_LOCATION_PROPERTY;

import com.apache.gravitino.catalog.lakehouse.iceberg.IcebergTablePropertiesMetadata;
import com.apache.gravitino.catalog.property.PropertyConverter;
import com.apache.gravitino.connector.BasePropertiesMetadata;
import com.apache.gravitino.connector.PropertyEntry;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.apache.commons.collections4.bidimap.TreeBidiMap;

public class IcebergTablePropertyConverter extends PropertyConverter {

  private final BasePropertiesMetadata icebergTablePropertiesMetadata =
      new IcebergTablePropertiesMetadata();

  // TODO (yuqi) add more properties
  @VisibleForTesting
  static final TreeBidiMap<String, String> TRINO_KEY_TO_GRAVITINO_KEY =
      new TreeBidiMap<>(
          new ImmutableMap.Builder<String, String>()
              .put(ICEBERG_LOCATION_PROPERTY, LOCATION)
              .build());

  @Override
  public TreeBidiMap<String, String> engineToGravitinoMapping() {
    // Note: As the properties for Iceberg table loaded from Gravitino are always empty currently,
    // no matter what the mapping is, the properties will be empty.
    return TRINO_KEY_TO_GRAVITINO_KEY;
  }

  @Override
  public Map<String, PropertyEntry<?>> gravitinoPropertyMeta() {
    return icebergTablePropertiesMetadata.propertyEntries();
  }
}
