/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.core.jmx.query;

/**
 * 
 * Service that tracks statistics for queries. <br>
 * 
 * It acts as a <b>Top X</b> longest running queries.
 * 
 */
public interface QueryStatManagerBase {

    /**
     * @return how big the <b>Top X</b> queue is
     */
    int getQueueSize();

    /**
     * Change the <b>Top X</b> queue size
     * 
     * @param size
     *            the new size
     */
    void setQueueSize(int size);

    /**
     * clears the queue
     */
    void clearQueue();

    /**
     * If this service is currently registering stats
     * 
     * @return <code>true</code> if the service is enabled
     */
    boolean isEnabled();

    /**
     * Enables/Disables the service
     * 
     * @param enabled
     */
    void setEnabled(boolean enabled);

}