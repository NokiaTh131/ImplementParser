public enum Direction {
    UP, DOWN,UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT;

    public void prettyPrint(StringBuilder s) {
        s.append(this.name().toLowerCase());
    }
}