package org.purwa.crud_process.data;

import java.util.List;

public record ShipmentDataRequest(Long customerId, List<ItemDataRequest> items ) {

}
