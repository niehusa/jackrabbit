/*
 * Copyright 2004-2005 The Apache Software Foundation or its licensors,
 *                     as applicable.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.test.api.observation;

import org.apache.jackrabbit.test.NotExecutableException;

import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.RepositoryException;
import java.util.NoSuchElementException;

/**
 * Tests the methods the following methods:
 * <ul>
 * <li>{@link javax.jcr.observation.EventIterator#getSize()}</li>
 * <li>{@link javax.jcr.observation.EventIterator#getPos()}</li>
 * <li>{@link javax.jcr.observation.EventIterator#skip(long)}</li>
 * </ul>
 * <p/>
 * Configuration requirements are:<br/>
 * The {@link #testRoot} must allow child nodes of type {@link #testNodeType}.
 * The child nodes that are created will be named {@link #nodeName1},
 * {@link #nodeName2} and {@link #nodeName3}.
 *
 * @test
 * @sources EventIteratorTest.java
 * @executeClass org.apache.jackrabbit.test.api.observation.EventIteratorTest
 * @keywords observation
 */
public class EventIteratorTest extends AbstractObservationTest{

    /**
     * Tests if getSize() returns the correct number of events. If getSize()
     * returns -1 a {@link org.apache.jackrabbit.test.NotExecutableException}
     * is thrown.
     */
    public void testGetSize() throws RepositoryException, NotExecutableException {
        EventResult listener = new EventResult(log);
        addEventListener(listener, Event.NODE_ADDED);
        testRootNode.addNode(nodeName1, testNodeType);
        testRootNode.save();
        removeEventListener(listener);
        EventIterator events = listener.getEventIterator(DEFAULT_WAIT_TIMEOUT);
        assertNotNull("No events delivered within " + DEFAULT_WAIT_TIMEOUT + "ms.", events);
        long size = events.getSize();
        if (size == -1) {
            throw new NotExecutableException("EventIterator.getSize() returns unavailable size.");
        }
        assertEquals("Wrong number of events", 1, size);
    }

    /**
     * Tests if getPos() returns the correct values.
     */
    public void testGetPos() throws RepositoryException {
        EventResult listener = new EventResult(log);
        addEventListener(listener, Event.NODE_ADDED);
        testRootNode.addNode(nodeName1, testNodeType);
        testRootNode.addNode(nodeName2, testNodeType);
        testRootNode.addNode(nodeName3, testNodeType);
        testRootNode.save();
        removeEventListener(listener);
        EventIterator events = listener.getEventIterator(DEFAULT_WAIT_TIMEOUT);
        assertNotNull("No events delivered within " + DEFAULT_WAIT_TIMEOUT + "ms.", events);
        assertEquals("Initial call to getPos() must return 0.", 0, events.getPos());
        events.nextEvent();
        assertEquals("Wrong value for getPos()", 1, events.getPos());
        events.nextEvent();
        assertEquals("Wrong value for getPos()", 2, events.getPos());
        events.nextEvent();
        assertEquals("Wrong value for getPos()", 3, events.getPos());
    }

    /**
     * Tests the method skip()
     */
    public void testSkip() throws RepositoryException {
        EventResult listener = new EventResult(log);
        addEventListener(listener, Event.NODE_ADDED);
        testRootNode.addNode(nodeName1, testNodeType);
        testRootNode.addNode(nodeName2, testNodeType);
        testRootNode.addNode(nodeName3, testNodeType);
        testRootNode.save();
        removeEventListener(listener);
        EventIterator events = listener.getEventIterator(DEFAULT_WAIT_TIMEOUT);
        assertNotNull("No events delivered within " + DEFAULT_WAIT_TIMEOUT + "ms.", events);
        // skip zero elements
        events.skip(0);
        assertEquals("getPos() for first element must return 0.", 0, events.getPos());
        // skip one element
        events.skip(2);
        assertEquals("Wrong value for getPos()", 2, events.getPos());
        // skip past end
        try {
            events.skip(2);
            fail("EventIterator must throw NoSuchElementException when skipping past the end");
        } catch (NoSuchElementException e) {
            // success
        }
    }
}