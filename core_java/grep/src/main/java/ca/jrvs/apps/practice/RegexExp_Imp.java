package ca.jrvs.apps.practice;

public class RegexExp_Imp implements RegexExp {

    @Override
    /* use the string.match() method to specify a regex pattern, and this method returns a boolean. */
    public boolean matchIp(String ip) {
        return ip.matches("^([\\d]{1,3}\\.){3}[\\d]{1,3}$");
    }

    @Override
    public boolean isEmptyLine(String line) {
        return line.matches("^\\s*$");
    }

    @Override
    public boolean matchJpeg(String filename) {
        return filename.matches("([a-z0-9A-z]|[\\-])+\\.jp[e]?g$");
    }

    public static void main(String[] args) {
        RegexExp_Imp imp = new RegexExp_Imp();
        System.out.println("Valid Ip :" + imp.matchIp("1.1.23.45"));
        System.out.println("isEmpty Line :" + imp.isEmptyLine(" "));
        System.out.println("match jpeg:" + imp.matchJpeg("image.jpeg"));
    }

}