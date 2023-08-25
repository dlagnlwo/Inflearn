package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void save(Item item) throws Exception {
        if(item.getId() == null) {
            itemRepository.save(item);
        }else {
            throw new Exception("not found : " + item);
        }
        return;
    }

    public Item findById(Long id){
        return itemRepository.findOne(id);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item findByName(String name) {
        return itemRepository.findByName(name);
    }
}
