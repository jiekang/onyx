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

package org.onyx.main;

import java.io.IOException;

import org.onyx.builder.BytemanBuilder;
import org.onyx.builder.DirectoryException;

public class Start {

    public static void main(String[] args) {
        try {
            start(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(String[] args) throws DirectoryException, IOException {
        if (args.length == 2) {
            String searchDirectory = args[0];
            String outputDirectory = args[1];
            BytemanBuilder builder = new BytemanBuilder(searchDirectory, outputDirectory);
            builder.build();
        } else {
            System.out.println("Invalid argument. Expected two arguments denoting base search directory and output directory.");
        }
    }

}
