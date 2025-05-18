package hu.adatba.Service;

import hu.adatba.DAO.InvoiceDAO;
import hu.adatba.Model.Invoice;

import java.sql.SQLException;
import java.util.List;

public class InvoiceService {
    private final InvoiceDAO invoiceDAO;

    public InvoiceService() {
        this.invoiceDAO = new InvoiceDAO();
    }

    public List<Invoice> getAllInvoices(int UserID) throws SQLException {
        return invoiceDAO.getAllInvoices(UserID);
    }
}
