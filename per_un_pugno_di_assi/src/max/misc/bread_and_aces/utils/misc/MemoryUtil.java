/*******************************************************************************
 * Copyleft (c) 2015, "Massimiliano Leone - <maximilianus@gmail.com> - https://plus.google.com/+MassimilianoLeone"
 * This file (MemoryUtil.java) is part of facri.
 * 
 *     MemoryUtil.java is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     MemoryUtil.java is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with .  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package bread_and_aces.utils.misc;

import bread_and_aces.utils.DevPrinter;

public class MemoryUtil {
	
	private static final long MEGABYTE = 1024L * 1024L;

	public static float bytesToMegabytes(long bytes) {
		return bytes*1.0f / MEGABYTE;
	}
	
	public static float runGarbageCollector() {
		// Get the Java runtime
		Runtime runtime = Runtime.getRuntime();
		// Run the garbage collector
		runtime.gc();
		// Calculate the used memory
		long memory = runtime.totalMemory() - runtime.freeMemory();
//System.out.println("Used memory is bytes: " + memory);
		float bytesToMegabytes = bytesToMegabytes(memory);
DevPrinter.println("Memory released is: " + bytesToMegabytes+"Mb");
		return bytesToMegabytes;
	}

}
