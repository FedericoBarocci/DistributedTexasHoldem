/*******************************************************************************
 * Copyleft (c) 2014, "Massimiliano Leone - <maximilianus@gmail.com> - https://plus.google.com/+MassimilianoLeone"
 * This file (ConsolePrinter.java) is part of WordsRelatorByBabelnet.
 * 
 *     ConsolePrinter.java is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     ConsolePrinter.java is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with .  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package breads_and_aces.utils.printer;


import java.io.Serializable;

//@ImplementedBy(ConsolePrinter.ConsolePrinterDummy.class)
public interface ConsolePrinter extends Printer {
	
	public static class ConsolePrinterReal implements ConsolePrinter, Serializable {

		private static final long serialVersionUID = -7018609970922495583L;

		@Override
		public void print(Object string) {
			System.out.print(""+string);
		}
		@Override
		public void println(Object string) {
			System.out.println(""+string);
		}
	}
	
	public static class ConsolePrinterDummy implements ConsolePrinter {
		@Override
		public void print(Object string) {}
		@Override
		public void println(Object string) {}
	}
}
