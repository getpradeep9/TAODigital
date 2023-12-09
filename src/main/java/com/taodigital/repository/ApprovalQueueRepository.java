package com.taodigital.repository;


import com.taodigital.entity.ApprovalQueueEntity;
import com.taodigital.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueueEntity, Long> {

    public ApprovalQueueEntity findByproductEntity(ProductEntity productEntity);

    public ApprovalQueueEntity deleteByproductEntity(ProductEntity productEntity);
}
