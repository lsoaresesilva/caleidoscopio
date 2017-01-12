/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 *
 * @author macbookair
 */
public class TokenGenerator implements IdentifierGenerator{

    
    @Override
    public Serializable generate(SharedSessionContractImplementor ssci, Object o) throws HibernateException {

        Model p = (Model)o;
        if( p.getToken().equals("") )
            return UUID.randomUUID().toString();
        else
            return p.getToken();
    }

    
     

    
}
