/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.sdk.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.hadoop.util.AbstractGoogleAsyncWriteChannel;
import java.util.concurrent.ExecutorService;
import javax.annotation.Nullable;
import org.apache.beam.sdk.extensions.gcp.options.GcpOptions;
import org.apache.beam.sdk.util.GcsPathValidator;
import org.apache.beam.sdk.util.GcsUtil;
import org.apache.beam.sdk.util.PathValidator;

/**
 * Options used to configure Google Cloud Storage.
 */
@Deprecated
public interface GcsOptions extends
    ApplicationNameOptions, GcpOptions, PipelineOptions {
  /**
   * The GcsUtil instance that should be used to communicate with Google Cloud Storage.
   */
  @JsonIgnore
  @Description("The GcsUtil instance that should be used to communicate with Google Cloud Storage.")
  @Default.InstanceFactory(GcsUtil.GcsUtilFactory.class)
  @Hidden
  GcsUtil getGcsUtil();
  void setGcsUtil(GcsUtil value);

  /**
   * The ExecutorService instance to use to create threads, can be overridden to specify an
   * ExecutorService that is compatible with the users environment. If unset, the
   * default is to create an ExecutorService with an unbounded number of threads; this
   * is compatible with Google AppEngine.
   */
  @JsonIgnore
  @Description("The ExecutorService instance to use to create multiple threads. Can be overridden "
      + "to specify an ExecutorService that is compatible with the users environment. If unset, "
      + "the default is to create an ExecutorService with an unbounded number of threads; this "
      + "is compatible with Google AppEngine.")
  @Default.InstanceFactory(
      org.apache.beam.sdk.extensions.gcp.options.GcsOptions.ExecutorServiceFactory.class)
  @Hidden
  ExecutorService getExecutorService();
  void setExecutorService(ExecutorService value);

  /**
   * GCS endpoint to use. If unspecified, uses the default endpoint.
   */
  @JsonIgnore
  @Hidden
  @Description("The URL for the GCS API.")
  String getGcsEndpoint();
  void setGcsEndpoint(String value);

  /**
   * The buffer size (in bytes) to use when uploading files to GCS. Please see the documentation for
   * {@link AbstractGoogleAsyncWriteChannel#setUploadBufferSize} for more information on the
   * restrictions and performance implications of this value.
   */
  @Description("The buffer size (in bytes) to use when uploading files to GCS. Please see the "
      + "documentation for AbstractGoogleAsyncWriteChannel.setUploadBufferSize for more "
      + "information on the restrictions and performance implications of this value.\n\n"
      + "https://github.com/GoogleCloudPlatform/bigdata-interop/blob/master/util/src/main/java/"
      + "com/google/cloud/hadoop/util/AbstractGoogleAsyncWriteChannel.java")
  @Nullable
  Integer getGcsUploadBufferSizeBytes();
  void setGcsUploadBufferSizeBytes(@Nullable Integer bytes);

  /**
   * The class of the validator that should be created and used to validate paths.
   * If pathValidator has not been set explicitly, an instance of this class will be
   * constructed and used as the path validator.
   */
  @Description("The class of the validator that should be created and used to validate paths. "
      + "If pathValidator has not been set explicitly, an instance of this class will be "
      + "constructed and used as the path validator.")
  @Default.Class(GcsPathValidator.class)
  Class<? extends PathValidator> getPathValidatorClass();
  void setPathValidatorClass(Class<? extends PathValidator> validatorClass);

  /**
   * The path validator instance that should be used to validate paths.
   * If no path validator has been set explicitly, the default is to use the instance factory that
   * constructs a path validator based upon the currently set pathValidatorClass.
   */
  @JsonIgnore
  @Description("The path validator instance that should be used to validate paths. "
      + "If no path validator has been set explicitly, the default is to use the instance factory "
      + "that constructs a path validator based upon the currently set pathValidatorClass.")
  @Default.InstanceFactory(
      org.apache.beam.sdk.extensions.gcp.options.GcsOptions.PathValidatorFactory.class)
  PathValidator getPathValidator();
  void setPathValidator(PathValidator validator);
}
