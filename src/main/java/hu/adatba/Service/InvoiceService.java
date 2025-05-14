package hu.adatba.Service;

import hu.adatba.DAO.InvoiceDAO;
import hu.adatba.DAO.OrderDAO;
import hu.adatba.Model.Invoice;

import java.sql.SQLException;
import java.util.List;

public class InvoiceService {
    private final InvoiceDAO invoiceDAO;

    public InvoiceService() throws SQLException {
        this.invoiceDAO = new InvoiceDAO();
    }

    public List<Invoice> getAllInvoices(String type){
        return invoiceDAO.getAllInvoices(type);
    }
}
