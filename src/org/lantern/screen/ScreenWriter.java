/*
 *  Copyright (C) 2010 mabe02
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.lantern.screen;

import java.util.Arrays;
import java.util.HashSet;
import org.lantern.terminal.Terminal;
import org.lantern.terminal.Terminal.Color;
import org.lantern.terminal.TerminalPosition;

/**
 *
 * @author mabe02
 */
public class ScreenWriter
{
    private final Screen targetScreen;
    private final TerminalPosition currentPosition;
    private Terminal.Color foregroundColor;
    private Terminal.Color backgroundColor;

    public ScreenWriter(final Screen targetScreen)
    {
        this.foregroundColor = Color.DEFAULT;
        this.backgroundColor = Color.DEFAULT;
        this.targetScreen = targetScreen;
        this.currentPosition = new TerminalPosition(0, 0);
    }

    public int getWidth()
    {
        return targetScreen.getWidth();
    }

    public int getHeight()
    {
        return targetScreen.getHeight();
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(final Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public Color getForegroundColor()
    {
        return foregroundColor;
    }

    public void setForegroundColor(final Color foregroundColor)
    {
        this.foregroundColor = foregroundColor;
    }

    public void fillScreen(char c)
    {
        int screenWidth = targetScreen.getWidth();
        int screenHeight = targetScreen.getHeight();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < screenWidth; i++)
            sb.append(c);

        String line = sb.toString();
        for(int i = 0; i < screenHeight; i++) {
            drawString(0, i, line);
        }
    }

    public void drawString(final int x, final int y, final String string, final Terminal.Style... styles)
    {
        currentPosition.setColumn(x);
        currentPosition.setRow(y);

        final HashSet<Terminal.Style> drawStyle = new HashSet<Terminal.Style>(Arrays.asList(styles));
        targetScreen.putString(x, y, string, foregroundColor, backgroundColor,
                drawStyle.contains(Terminal.Style.Bold), drawStyle.contains(Terminal.Style.Underline),
                drawStyle.contains(Terminal.Style.Reverse));
        currentPosition.setColumn(currentPosition.getColumn() + string.length());
    }

    @Override
    public int hashCode()
    {
        return targetScreen.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof ScreenWriter == false)
            return false;

        return targetScreen.equals(((ScreenWriter)(obj)).targetScreen);
    }
}