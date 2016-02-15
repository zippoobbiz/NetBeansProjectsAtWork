/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasecontroller.entity;

/**
 *
 * @author Phil
 */
public class Pair {

    private String company;

    public Pair() {
    }

    public Pair(String company, String shareClass1, String shareClass2) {
        this.company = company;
        this.shareClass1 = shareClass1;
        this.shareClass2 = shareClass2;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getShareClass1() {
        return shareClass1;
    }

    public void setShareClass1(String shareClass1) {
        this.shareClass1 = shareClass1;
    }

    public String getShareClass2() {
        return shareClass2;
    }

    public String getShare1CompanyName() {
        return share1CompanyName;
    }

    public void setShare1CompanyName(String share1CompanyName) {
        this.share1CompanyName = share1CompanyName;
    }

    public String getShare2CompanyName() {
        return share2CompanyName;
    }

    public void setShare2CompanyName(String share2CompanyName) {
        this.share2CompanyName = share2CompanyName;
    }

    public void setShareClass2(String shareClass2) {
        this.shareClass2 = shareClass2;
    }
    private String shareClass1;
    private String shareClass2;
    private String share1CompanyName;
    private String share2CompanyName;
    private String share2Name;

    public String getShare1Name() {
        return share1Name;
    }

    public void setShare1Name(String share1Name) {
        this.share1Name = share1Name;
    }

    public String getShare2Name() {
        return share2Name;
    }

    public void setShare2Name(String share2Name) {
        this.share2Name = share2Name;
    }
    private String share1Name;
}
