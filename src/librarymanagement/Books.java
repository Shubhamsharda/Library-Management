/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagement;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Shubham
 */
@Entity
@Table(name = "books", catalog = "lib", schema = "")
@NamedQueries({
    //@NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b"),
   // @NamedQuery(name = "Books.findByBookname", query = "SELECT b FROM Books b WHERE b.bookname = :bookname"),
    @NamedQuery(name = "Books.findByBookno", query = "SELECT b FROM Books b WHERE b.bookno = :bookno"),
    //@NamedQuery(name = "Books.findByIssueStatus", query = "SELECT b FROM Books b WHERE b.issueStatus = :issueStatus")
})
public class Books implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Column(name = "bookname")
    private String bookname;
    @Id
    @Basic(optional = false)
    @Column(name = "bookno")
    private Integer bookno;
    @Column(name = "IssueStatus")
    private String issueStatus;

    public Books() {
    }

    public Books(Integer bookno) {
        this.bookno = bookno;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        String oldBookname = this.bookname;
        this.bookname = bookname;
        changeSupport.firePropertyChange("bookname", oldBookname, bookname);
    }

    public Integer getBookno() {
        return bookno;
    }

    public void setBookno(Integer bookno) {
        Integer oldBookno = this.bookno;
        this.bookno = bookno;
        changeSupport.firePropertyChange("bookno", oldBookno, bookno);
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        String oldIssueStatus = this.issueStatus;
        this.issueStatus = issueStatus;
        changeSupport.firePropertyChange("issueStatus", oldIssueStatus, issueStatus);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookno != null ? bookno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Books)) {
            return false;
        }
        Books other = (Books) object;
        if ((this.bookno == null && other.bookno != null) || (this.bookno != null && !this.bookno.equals(other.bookno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "librarymanagement.Books[ bookno=" + bookno + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
