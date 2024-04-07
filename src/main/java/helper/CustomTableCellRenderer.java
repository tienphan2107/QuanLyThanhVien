package helper;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableCellRenderer {

    public static class AlignmentCellRenderer extends DefaultTableCellRenderer {

        private final int alignment;

        public AlignmentCellRenderer(int alignment) {
            this.alignment = alignment;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Set the alignment based on the specified value
            switch (alignment) {
                case SwingConstants.LEFT:
                    setHorizontalAlignment(SwingConstants.LEFT);
                    break;
                case SwingConstants.CENTER:
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                case SwingConstants.RIGHT:
                    setHorizontalAlignment(SwingConstants.RIGHT);
                    break;
            }

            return renderer;
        }
    }

    public final static DefaultTableCellRenderer LEFT = new AlignmentCellRenderer(SwingConstants.LEFT);
    public final static DefaultTableCellRenderer CENTER = new AlignmentCellRenderer(SwingConstants.CENTER);
    public final static DefaultTableCellRenderer RIGHT = new AlignmentCellRenderer(SwingConstants.RIGHT);

}
