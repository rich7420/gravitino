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
package com.apache.gravitino.server.web.rest;

import com.apache.gravitino.GravitinoEnv;
import com.apache.gravitino.authorization.AccessControlManager;
import com.apache.gravitino.dto.requests.RoleGrantRequest;
import com.apache.gravitino.dto.requests.RoleRevokeRequest;
import com.apache.gravitino.dto.responses.GroupResponse;
import com.apache.gravitino.dto.responses.UserResponse;
import com.apache.gravitino.dto.util.DTOConverters;
import com.apache.gravitino.metrics.MetricNames;
import com.apache.gravitino.server.web.Utils;
import com.codahale.metrics.annotation.ResponseMetered;
import com.codahale.metrics.annotation.Timed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

@Path("/metalakes/{metalake}/permissions")
public class PermissionOperations {

  private final AccessControlManager accessControlManager;

  @Context private HttpServletRequest httpRequest;

  public PermissionOperations() {
    // Because accessManager may be null when Gravitino doesn't enable authorization,
    // and Jersey injection doesn't support null value. So PermissionOperations chooses to retrieve
    // accessControlManager from GravitinoEnv instead of injection here.
    this.accessControlManager = GravitinoEnv.getInstance().accessControlManager();
  }

  @PUT
  @Path("users/{user}/grant/")
  @Produces("application/vnd.gravitino.v1+json")
  @Timed(name = "grant-roles-to-user." + MetricNames.HTTP_PROCESS_DURATION, absolute = true)
  @ResponseMetered(name = "grant-roles-to-user", absolute = true)
  public Response grantRolesToUser(
      @PathParam("metalake") String metalake,
      @PathParam("user") String user,
      RoleGrantRequest request) {
    try {
      return Utils.doAs(
          httpRequest,
          () ->
              Utils.ok(
                  new UserResponse(
                      DTOConverters.toDTO(
                          accessControlManager.grantRolesToUser(
                              metalake, request.getRoleNames(), user)))));
    } catch (Exception e) {
      return ExceptionHandlers.handleUserPermissionOperationException(
          OperationType.GRANT, StringUtils.join(request.getRoleNames(), ","), user, e);
    }
  }

  @PUT
  @Path("groups/{group}/grant/")
  @Produces("application/vnd.gravitino.v1+json")
  @Timed(name = "grant-roles-to-group." + MetricNames.HTTP_PROCESS_DURATION, absolute = true)
  @ResponseMetered(name = "grant-roles-to-group", absolute = true)
  public Response grantRolesToGroup(
      @PathParam("metalake") String metalake,
      @PathParam("group") String group,
      RoleGrantRequest request) {
    try {
      return Utils.doAs(
          httpRequest,
          () ->
              Utils.ok(
                  new GroupResponse(
                      DTOConverters.toDTO(
                          accessControlManager.grantRolesToGroup(
                              metalake, request.getRoleNames(), group)))));
    } catch (Exception e) {
      return ExceptionHandlers.handleGroupPermissionOperationException(
          OperationType.GRANT, StringUtils.join(request.getRoleNames(), ","), group, e);
    }
  }

  @PUT
  @Path("users/{user}/revoke/")
  @Produces("application/vnd.gravitino.v1+json")
  @Timed(name = "revoke-roles-from-user." + MetricNames.HTTP_PROCESS_DURATION, absolute = true)
  @ResponseMetered(name = "revoke-roles-from-user", absolute = true)
  public Response revokeRolesFromUser(
      @PathParam("metalake") String metalake,
      @PathParam("user") String user,
      RoleRevokeRequest request) {
    try {
      return Utils.doAs(
          httpRequest,
          () ->
              Utils.ok(
                  new UserResponse(
                      DTOConverters.toDTO(
                          accessControlManager.revokeRolesFromUser(
                              metalake, request.getRoleNames(), user)))));
    } catch (Exception e) {
      return ExceptionHandlers.handleUserPermissionOperationException(
          OperationType.REVOKE, StringUtils.join(request.getRoleNames(), ","), user, e);
    }
  }

  @PUT
  @Path("groups/{group}/revoke")
  @Produces("application/vnd.gravitino.v1+json")
  @Timed(name = "revoke-roles-from-group." + MetricNames.HTTP_PROCESS_DURATION, absolute = true)
  @ResponseMetered(name = "revokes-role-from-group", absolute = true)
  public Response revokeRolesFromGroup(
      @PathParam("metalake") String metalake,
      @PathParam("group") String group,
      RoleRevokeRequest request) {
    try {
      return Utils.doAs(
          httpRequest,
          () ->
              Utils.ok(
                  new GroupResponse(
                      DTOConverters.toDTO(
                          accessControlManager.revokeRolesFromGroup(
                              metalake, request.getRoleNames(), group)))));
    } catch (Exception e) {
      return ExceptionHandlers.handleGroupPermissionOperationException(
          OperationType.REVOKE, StringUtils.join(request.getRoleNames()), group, e);
    }
  }
}
