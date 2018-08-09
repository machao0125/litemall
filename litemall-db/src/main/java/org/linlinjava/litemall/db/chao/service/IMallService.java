package org.linlinjava.litemall.db.chao.service;



import org.linlinjava.litemall.db.chao.domain.Mall;

import java.util.List;

/**
 * Created by machao on 2018/8/5.
 */
public interface IMallService {

    List<Mall> mallListNearby(String longiandlatitude, int num);

    Mall mallListByUserId(int userId);
}
