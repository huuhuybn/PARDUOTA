package com.parduota.parduota.face;

import com.parduota.parduota.model.order.Datum;
import com.parduota.parduota.model.order.Order;

/**
 * Created by huy_quynh on 10/30/17.
 */

public interface OnOrderClick {

    void onClick(Datum order);
}
