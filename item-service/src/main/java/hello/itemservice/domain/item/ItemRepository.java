package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    //실제는 HashMap<>() 사용하면 안됨.
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    //save : Item을 반환해야함.
    public Item save(Item item){
        //sequence값 증가시킨걸 id에 넣음
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    //조회
    public Item findById(Long id) {
        return store.get(id);
    }

    //전체조회
    public List<Item> findAll(){
        //감싸서 반환하면 ArrayList에 값을 넣어도 실제로 바뀌지 않음.
        return new ArrayList<>(store.values());
    }

    //수정
    //원래는 ItemName, setPrice, setQuantity 필드가 있는
    //ItemDTO같은거를 하나 만들어서 객체를 통째로 해야함.
    //즉, DTO 클래스를 만들어야함. 여기는 작은규모의 프로젝트이기때문에 일케함
    public void update(Long itemId, Item updateParam){
        //수정을 하려면 먼저 아이디 조회부터 해야됨.
        Item findItem = findById(itemId);
        //조회한거에 이름에 updateParam의 ItemName을 저장시킴
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStroe(){
        store.clear();
    }
}
