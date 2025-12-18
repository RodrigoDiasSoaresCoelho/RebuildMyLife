package br.com.jesusc.rebuildmylife.model

import br.com.jesusc.rebuildmylife.enums.EnumNotification
import br.com.jesusc.rebuildmylife.enums.EnumPriority

data class Notification(
    var enumNotification: EnumNotification = EnumNotification.SIMPLE,
) {

}