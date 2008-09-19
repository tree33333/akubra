/* $HeadURL::                                                                            $
 * $Id$
 *
 * Copyright (c) 2007-2008 by Fedora Commons Inc.
 * http://www.fedoracommons.org
 *
 * In collaboration with Topaz Inc.
 * http://www.topazproject.org
 *
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
 */
package org.fedoracommons.akubra;

import java.io.IOException;
import java.net.URI;

import java.util.Iterator;
import java.util.Map;

/**
 * Interface to abstract the idea of a connection to a transaction based blob store
 *
 * @author Chris Wilper
 * @author Pradeep Krishnan
 * @author Ronald Tschalär
 */
public interface BlobStoreConnection {
  /**
   * Gets the blob store associated with this session.
   *
   * @return the blob store.
   */
  BlobStore getBlobStore();

  /**
   * Return the blob associated with the blob-id
   *
   * @param blobId URI identifying the blob
   * @param hints A set of hints to allow the implementation to optimize the operation (can be
   *              null)
   *
   * @return the blob or null in case of no blob with the blob-id
   *
   * @exception IOException for IO errors
   */
  Blob getBlob(URI blobId, Map<String, String> hints) throws IOException;

  /**
   * Stores a blob in the store.
   *
   * If a blob already exists with the blob-id, then the blob will be overwritten.  Compliant stores
   * expect applications to not modify the passed blob till the transaction is completed (commit or
   * rollback).
   *
   * @param blobId    the blob-id for the blob. If the blob-id is null then the store is required to
   *                  assign one to the blob.
   * @param blob      the blob to store.
   * @param hints A set of hints to allow the implementation to optimize the operation (can be
   *              null)
   *
   * @return the blob blob-id.
   *
   * @exception IOException for IO errors
   */
  URI putBlob(URI blobId, Blob blob, Map<String, String> hints) throws IOException;

  /**
   * Remove the blob from the store
   *
   * @param blobId URI identifying the blob
   * @param hints A set of hints to allow the implementation to optimize the operation (can be
   *              null)
   *
   * @return URI locatator-id of the deleted blob or null in case of no blob found
   *
   * @exception IOException for IO errors
   */
  URI removeBlob(URI blobId, Map<String, String> hints) throws IOException;

  /**
   * Gets an iterator over the ids of all blobs in this store.
   *
   * @param filterPrefix If provided, the list will be limited to those blob-ids beginning with this prefix
   *
   * @return The iterator of blob-ids.
   */
  Iterator<URI> listBlobIds(String filterPrefix);

  /**
   * Close the connection to the blob store
   */
  void close();
}
