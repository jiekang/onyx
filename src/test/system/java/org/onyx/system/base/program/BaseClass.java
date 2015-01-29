/*
    Onyx Alpha
    Copyright (C) 2014  Jie Kang

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.onyx.system.base.program;

import java.util.concurrent.atomic.AtomicBoolean;

public class BaseClass {

    private final long DEFAULT_WAIT = 1000l;
    private AtomicBoolean b = new AtomicBoolean(true);

    public void begin() throws InterruptedException {
        if (b.get()) {
            Thread.sleep(DEFAULT_WAIT);
            methodOne();
        }
    }

    private void methodOne() throws InterruptedException {
        if (b.get()) {
            Thread.sleep(DEFAULT_WAIT);
            methodTwo();
        }
    }

    private void methodTwo() throws InterruptedException {
        if (b.get()) {
            Thread.sleep(DEFAULT_WAIT);
            methodThree();
            methodFour();
            methodOne();
        }
    }

    private void methodThree() throws InterruptedException {
        if (b.get()) {
            Thread.sleep(DEFAULT_WAIT);
        }
    }

    private void methodFour() throws InterruptedException {
        if (b.get()) {
            Thread.sleep(DEFAULT_WAIT);
        }
    }

    public void stop() {
        b.set(false);
    }
}
