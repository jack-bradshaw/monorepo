package com.jackbradshaw.kale.resolver.rule

import com.jackbradshaw.closet.rule.ClosetRuleTemplate
import com.jackbradshaw.kale.resolver.chassis.ResolverChassis
import com.jackbradshaw.kale.resolver.chassis.ResolverChassisComponent
import com.jackbradshaw.kale.resolver.chassis.resolverChassisComponent

/**
 * Provides a [ResolverChassis] and closes it automatically during test tear down.
 *
 * The default [component] can be replaced to take manual control of the dependency graph that
 * supplies the [ResolverChassis] while retaining the auto-closure functionality.
 */
class ResolverTestRule(
    private val component: ResolverChassisComponent = resolverChassisComponent()
) : ClosetRuleTemplate<ResolverChassis>() {
  override fun initialiseResource() = component.resolverChassis()
}
