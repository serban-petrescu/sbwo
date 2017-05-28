package spet.sbwo.control.channel;

import java.util.HashMap;
import java.util.Map;

public class UserHomeTilesChannel {
    private Map<String, HomeTile> tiles;

    public UserHomeTilesChannel() {
        this.tiles = new HashMap<>();
    }

    public Map<String, HomeTile> getTiles() {
        return tiles;
    }

    public void setTiles(Map<String, HomeTile> tiles) {
        this.tiles = tiles;
    }

    public void addTile(String name, int order, boolean visible) {
        this.tiles.put(name, new HomeTile(order, visible));
    }

    public static class HomeTile {
        private int order;
        private boolean visible;

        public HomeTile() {
            super();
        }

        public HomeTile(int order, boolean visible) {
            super();
            this.order = order;
            this.visible = visible;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

    }
}
