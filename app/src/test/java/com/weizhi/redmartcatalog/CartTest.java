package com.weizhi.redmartcatalog;

import com.weizhi.redmartcatalog.model.Cart;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class CartTest {
    @Test
    public void testClearCart() throws Exception {
        Cart cart = new Cart(2);

        cart.addToCart(1, 1);
        assertEquals(1, cart.getQuantity(1));

        cart.clearCart();
        assertEquals(0, cart.getQuantity(1));
    }

    @Test
    public void testAddToCart() throws Exception {
        Cart cart = new Cart(2);

        cart.addToCart(1, 1);
        assertEquals(1, cart.getQuantity(1));

        cart.addToCart(1, 1);
        assertEquals(2, cart.getQuantity(1));

        cart.addToCart(1, 1);
        assertEquals(2, cart.getQuantity(1));
    }

    @Test
    public void testRemoveFromCart() throws Exception {
        Cart cart = new Cart(2);

        cart.removeFromCart(1, 1);
        assertEquals(0, cart.getQuantity(1));

        cart.addToCart(1, 1);
        assertEquals(1, cart.getQuantity(1));
        cart.removeFromCart(1, 1);
        assertEquals(0, cart.getQuantity(1));
    }
}
