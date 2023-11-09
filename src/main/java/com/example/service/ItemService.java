package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Item;
import com.example.form.ItemForm;
import com.example.repository.ItemRepository;

@Service
public class ItemService {

	private final ItemRepository itemRepository;
	
	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public List<Item> findAll(){
		return this.itemRepository.findAll();
	}
	
	//データ保存用のメソッドです
	public Item save(ItemForm itemForm) {
		//Entityクラスのインスタンスを生成します
		Item item = new Item();
		//フィールドのセットを行います
		item.setName(itemForm.getName());
		item.setPrice(itemForm.getPrice());
		//repository.saveメソッドを利用してデータの保存を行います
		return this.itemRepository.save(item);
	}
	
	public Item findById(Integer id) {
		Optional<Item> optionalItem = this.itemRepository.findById(id);
		Item item = optionalItem.get();
		return item;
	}
	
	public Item update(Integer id,ItemForm itemForm) {
		//データ1件分のEntityクラスを取得します
		Item item = this.findById(id);
		//Formクラスのフィールドをセットします
		item.setName(itemForm.getName());
		item.setPrice(itemForm.getPrice());
		//repository.saveメソッドを利用してデータの保存を行います
		return this.itemRepository.save(item);
	}
	
	//saveメソッドはEntityを返り値に持つため、返り値の型を変更
		public Item delete(Integer id) {
			//idから該当のEntityクラスを取得します
			Item item = this.findById(id);
			//EntityクラスのdeletedAtフィールドを現在日時で上書きします
			item.setDeleteAt(LocalDateTime.now());
			//更新処理
			return this.itemRepository.save(item);
		}
	
	public List<Item> findByDeletedAtIsNull(){
		return this.itemRepository.findByDeletedAtIsNull();
	}
	
}
