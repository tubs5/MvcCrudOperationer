package com.example.mvccrudoperationer.service;

import com.example.mvccrudoperationer.model.InvoiceEntry;
import com.example.mvccrudoperationer.repository.InvoiceRepository;

import java.util.List;

/**
 * @author Tobias Heidlund
 */
@SuppressWarnings("UnusedReturnValue")
public class InvoiceService {
    private final InvoiceRepository invoiceRepository = new InvoiceRepository();
    public void create(InvoiceEntry invoiceEntry){
        invoiceRepository.create(invoiceEntry);
    }
    public InvoiceEntry get(int id, int owner){
       return invoiceRepository.get(owner,id);
    }
    public List<InvoiceEntry> getAll(int owner){
        return invoiceRepository.getAll(owner);
    }
    public boolean update(InvoiceEntry invoiceEntry){
        return invoiceRepository.update(invoiceEntry);
    }
    public boolean delete(int id, int user){
        return invoiceRepository.delete(id,user);
    }
}
