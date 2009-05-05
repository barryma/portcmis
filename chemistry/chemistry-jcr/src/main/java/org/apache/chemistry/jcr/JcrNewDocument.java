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
package org.apache.chemistry.jcr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.chemistry.ContentStream;
import org.apache.chemistry.Folder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.JcrConstants;

public class JcrNewDocument extends JcrDocument {

    private static final Log log = LogFactory.getLog(JcrNewDocument.class);

    private Node parent;
    private String name;
    private Map<String, Serializable> values = new HashMap<String, Serializable>();
    private ContentStream cs;
    private boolean saved;

    public JcrNewDocument(Node parent) {
        this.parent = parent;
    }

    public ContentStream getContentStream() {
        if (!saved) {
            throw new UnsupportedOperationException();
        }
        return super.getContentStream();
    }

    public Folder getParent() {
        if (!saved) {
            return new JcrFolder(parent);
        }
        return super.getParent();
    }

    public InputStream getStream() throws IOException {
        if (!saved) {
            throw new UnsupportedOperationException();
        }
        return super.getStream();
    }

    public void setContentStream(ContentStream contentStream)
            throws IOException {

        if (saved) {
            throw new UnsupportedOperationException();
        }
        this.cs = contentStream;
    }

    public void save() {
        if (saved) {
            throw new UnsupportedOperationException();
        }
        try {
            String name = this.name;
            if (cs != null) {
                name = cs.getFilename();
            }
            if (name == null) {
            	Serializable val = getValue("title");
            	if (val != null) {
            		name = val.toString();
            	}
            }
            if (name == null) {
            	name = "unnamed";
            }

            Node node = parent.addNode(name, JcrConstants.NT_FILE);
            node.addMixin(MIX_UNSTRUCTURED);
            Node content = node.addNode(JcrConstants.JCR_CONTENT,
                    JcrConstants.NT_RESOURCE);
            if (cs != null) {
                content.setProperty(JcrConstants.JCR_MIMETYPE, cs.getMimeType());
                content.setProperty(JcrConstants.JCR_DATA, cs.getStream());
            } else {
                content.setProperty(JcrConstants.JCR_MIMETYPE, "application/octet-stream");
                content.setProperty(JcrConstants.JCR_DATA, new ByteArrayInputStream(new byte[0]));
            }
            content.setProperty(JcrConstants.JCR_LASTMODIFIED, Calendar.getInstance());

            for (String key : values.keySet()) {
                node.setProperty(key, values.get(key).toString());
            }
            parent.save();
            setNode(node);
            saved = true;
        } catch (IOException e) {
            String msg = "Unable to get stream.";
            log.error(msg, e);
        } catch (RepositoryException e) {
            String msg = "Unable to save folder.";
            log.error(msg, e);
        }
    }

    public void setName(String name) {
        if (saved) {
            throw new UnsupportedOperationException();
        }
        this.name = name;
    }

    public void setValue(String name, Serializable value) {
        if (saved) {
            throw new UnsupportedOperationException();
        }
        values.put(name, value);
    }

    public void setValues(Map<String, Serializable> values) {
        if (saved) {
            throw new UnsupportedOperationException();
        }
        this.values.putAll(values);
    }
}
