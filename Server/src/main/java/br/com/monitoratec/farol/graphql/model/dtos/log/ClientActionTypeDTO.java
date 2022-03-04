package br.com.monitoratec.farol.graphql.model.dtos.log;

public enum ClientActionTypeDTO {
    SUBSCRIBED_CONTRACT,
    UNSUBSCRIBED_CONTRACT,
    ADDED_DEPENDENTS,
    REMOVED_DEPENDENTS,
    GOT_CONTRACT_REACTIVATED,
}
