/* SmartICT Bilisim A.S. (C) 2022 */
package com.smartict.mail.service;

import java.util.List;
import java.util.UUID;

public interface BaseService<D> {
    D create(D dto);

    D update(D dto);

    void delete(UUID id);

    D read(UUID id);

    List<D> readAll();
}
