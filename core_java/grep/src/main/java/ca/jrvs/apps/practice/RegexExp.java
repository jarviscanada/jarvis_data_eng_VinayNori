package ca.jrvs.apps.practice;

public interface RegexExp {
    /**
     * return true if filename extension is a jpg or jpeg (case-sensitive)
     * @param filename
     * @return
     */
    public boolean matchJpeg(String filename);

    /**
     * return true if ip is valid, i.e. from 0.0.0.0 to 999.999.999.999
     * @param ip
     * @return
     */
    public boolean matchIp(String ip);

    /**
     * return true if line is empty (e.g. empty, white space, tabs, etc...)
     * @param line
     * @return
     */
    public boolean isEmptyLine(String line);

}