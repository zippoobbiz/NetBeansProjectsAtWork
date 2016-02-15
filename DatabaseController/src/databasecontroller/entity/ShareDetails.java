/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasecontroller.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phil
 */
public class ShareDetails {

    public int getItemAmount() {
        return itemAmount;
    }

    public ShareDetails(Date d) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        itemAmount = 1;
        PBitemAmount = 1;
        lastUpdate = d;
        try {
            PBLastUpdate = fmt.parse("02-02-2015");
            PBRecentUpdate = fmt.parse("02-02-1999");
        } catch (ParseException ex) {
            Logger.getLogger(ShareDetails.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public Date getPBLastUpdate() {
        return PBLastUpdate;
    }

    public void setPBLastUpdate(Date PBLastUpdate) {
        this.PBLastUpdate = PBLastUpdate;
    }

    public int getPBitemAmount() {
        return PBitemAmount;
    }

    public void setPBitemAmount(int PBitemAmount) {
        this.PBitemAmount = PBitemAmount;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    private Date lastUpdate;
    private int itemAmount;
    private Date PBLastUpdate;
    private Date PBRecentUpdate;

    public Date getPBRecentUpdate() {
        return PBRecentUpdate;
    }

    public void setPBRecentUpdate(Date PBRecentUpdate) {
        this.PBRecentUpdate = PBRecentUpdate;
    }
    private int PBitemAmount;

    public void PBitemAmoutAutoPlus() {
        PBitemAmount++;
    }
}
