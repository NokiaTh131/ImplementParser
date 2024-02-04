public class CityCrew {
    private Player p;

    public CityCrew(Player p) {
        this.p = p;
    }

    public int getCurrentRow() {
        return p.bindings.get("currow");
    }

    public int getCurrentCol() {
        return p.bindings.get("curcol");
    }

    public int getPlayer_Id() {
        return p.getId();
    }

    public void moveUp() {
        p.bindings.put("currow",p.bindings.get("currow") - 1);
    }

    public void moveUpLeft() {
        if (getCurrentCol() % 2 == 0) {
            p.bindings.put("currow",p.bindings.get("currow") - 1);
            p.bindings.put("curcol",p.bindings.get("curcol") - 1);
            return;
        }
        if(getCurrentCol() % 2 == 1){
            p.bindings.put("curcol",p.bindings.get("curcol") - 1);
        }
    }

    public void moveUpRight() {
        if (getCurrentCol() % 2 == 0) {
            p.bindings.put("currow",p.bindings.get("currow") - 1);
            p.bindings.put("curcol",p.bindings.get("curcol") + 1);
            return;
        }
        if(getCurrentCol() % 2 == 1){
            p.bindings.put("curcol",p.bindings.get("curcol") + 1);
        }
    }

    public void moveDownRight() {
        if (getCurrentCol() % 2 == 0) {
            p.bindings.put("curcol",p.bindings.get("curcol") + 1);
            return;
        }
        if(getCurrentCol() % 2 == 1){
            p.bindings.put("curcol",p.bindings.get("curcol") + 1);
            p.bindings.put("currow",p.bindings.get("currow") + 1);
        }
    }

    public void moveDown() {
        p.bindings.put("currow",p.bindings.get("currow") + 1);
    }

    public void moveDownLeft() {
        if (getCurrentCol() % 2 == 0) {
            p.bindings.put("curcol",p.bindings.get("curcol") - 1);
            return;
        }
        if(getCurrentCol() % 2 == 1){
            p.bindings.put("curcol",p.bindings.get("curcol") - 1);
            p.bindings.put("currow",p.bindings.get("currow") + 1);
        }
    }


}
