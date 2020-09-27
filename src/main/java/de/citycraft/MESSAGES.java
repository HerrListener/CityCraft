package de.citycraft;

public enum MESSAGES {

    PREFIX("§eCity§6Craft §8⋙ §7"),
    NOPERM(MESSAGES.PREFIX.getText()+"&cDafür hast du keine Rechte (&4%no_perm%&c)");

    private String text;

    MESSAGES(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text.replaceAll("&","§");
    }

    public String noPermission(String permission) {
        return this.text.replaceAll("&","§").replaceAll("%no_perm%", permission);
    }

}
