package com.forge.service;

import java.io.Serializable;
import java.util.List;

import com.forge.bean.Forge_Cart;

public interface Forge_CartService extends BaseServise<Forge_Cart> {
	List<Forge_Cart> findByUserId(Serializable id);

}
